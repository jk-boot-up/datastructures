"""Markdown to a self-contained HTML page: styles inline, images embedded.

Covers what the course's Markdown uses: headings, paragraphs, lists (one
level, ordered or not), tables, fenced code, block quotes, rules, images and
inline code, bold, italics and links. Links to a .md file that has an .html
twin are rewritten to the twin, so the HTML pages link to each other.
"""

import base64
import html
import mimetypes
import re
from pathlib import Path

CSS = """
:root{color-scheme:light dark;--bg:#f8fafc;--fg:#0f172a;--muted:#475569;--card:#fff;--line:#d5dbe5;
--accent:#6d28d9;--code:#f1f5f9;--link:#1d4ed8}
@media (prefers-color-scheme:dark){:root{--bg:#0f172a;--fg:#e2e8f0;--muted:#94a3b8;--card:#1e293b;
--line:#334155;--accent:#a78bfa;--code:#020617;--link:#93c5fd}}
*{box-sizing:border-box}
body{margin:0;background:var(--bg);color:var(--fg);font:17px/1.65 -apple-system,"Segoe UI",Roboto,Helvetica,Arial,sans-serif;
overflow-wrap:anywhere}
main{max-width:920px;margin:0 auto;padding:28px 16px 40px}
h1{font-size:2rem;line-height:1.2;margin:.2em 0 .5em}
h2{font-size:1.45rem;margin:1.8em 0 .5em;padding-top:.4em;border-top:1px solid var(--line)}
h3{font-size:1.15rem;margin:1.4em 0 .4em}
a{color:var(--link)}
p,ul,ol{margin:.6em 0}
li{margin:.25em 0}
strong{color:var(--fg)}
code{font:.9em ui-monospace,"SF Mono",Menlo,Consolas,monospace;background:var(--code);padding:.1em .35em;border-radius:5px}
pre{background:var(--code);border:1px solid var(--line);border-radius:10px;padding:14px 16px;overflow-x:auto;line-height:1.45}
pre code{background:none;padding:0;font-size:.86rem}
blockquote{margin:1em 0;padding:.6em 1em;border-left:4px solid var(--accent);background:var(--card);border-radius:0 8px 8px 0}
table{border-collapse:collapse;width:100%;margin:1em 0;display:block;overflow-x:auto}
th,td{border:1px solid var(--line);padding:8px 10px;text-align:left;vertical-align:top}
th{background:var(--card)}
img{max-width:100%;height:auto;border-radius:10px;border:1px solid var(--line);background:#0f172a}
hr{border:0;border-top:1px solid var(--line);margin:2em 0}
footer{max-width:920px;margin:0 auto;padding:0 16px 28px;color:var(--muted);font-size:.85rem}
details{background:var(--card);border:1px solid var(--line);border-radius:10px;padding:.6em 1em;margin:1em 0}
summary{cursor:pointer;font-weight:700}
"""


def _inline(text, base):
    parts = re.split(r"(`[^`]+`)", text)
    out = []
    for part in parts:
        if part.startswith("`") and part.endswith("`") and len(part) > 1:
            out.append("<code>%s</code>" % html.escape(part[1:-1]))
            continue
        t = html.escape(part, quote=False)
        t = re.sub(r"!\[([^\]]*)\]\(([^)]+)\)", lambda m: _img(m.group(1), m.group(2), base), t)
        t = re.sub(r"\[([^\]]+)\]\(([^)]+)\)", lambda m: '<a href="%s">%s</a>' % (_href(m.group(2), base), m.group(1)), t)
        t = re.sub(r"\*\*(.+?)\*\*", r"<strong>\1</strong>", t)
        t = re.sub(r"(?<![\w*])\*(?!\s)(.+?)(?<!\s)\*(?![\w*])", r"<em>\1</em>", t)
        out.append(t)
    return "".join(out)


def _href(url, base):
    if re.match(r"^[a-z]+:", url) or url.startswith("#"):
        return url
    path, _, frag = url.partition("#")
    if path.endswith(".md") and (base / path).with_suffix(".html").exists():
        path = path[:-3] + ".html"
    elif path.endswith(".md") and path.endswith("README.md"):
        path = path[:-3] + ".html"
    return path + ("#" + frag if frag else "")


def _img(alt, src, base):
    f = (base / src)
    if f.exists():
        mime = mimetypes.guess_type(f.name)[0] or "image/png"
        if f.suffix == ".svg":
            return '<figure>%s</figure>' % f.read_text()
        data = base64.b64encode(f.read_bytes()).decode()
        return '<img alt="%s" src="data:%s;base64,%s">' % (html.escape(alt), mime, data)
    return '<img alt="%s" src="%s">' % (html.escape(alt), src)


def to_html(md, base):
    base = Path(base)
    lines = md.splitlines()
    out, i = [], 0
    while i < len(lines):
        ln = lines[i]
        if ln.startswith("```"):
            j = i + 1
            code = []
            while j < len(lines) and not lines[j].startswith("```"):
                code.append(lines[j])
                j += 1
            out.append("<pre><code>%s</code></pre>" % html.escape("\n".join(code)))
            i = j + 1
            continue
        m = re.match(r"^(#{1,6})\s+(.*)", ln)
        if m:
            n = len(m.group(1))
            ident = re.sub(r"[^a-z0-9]+", "-", m.group(2).lower()).strip("-")
            out.append('<h%d id="%s">%s</h%d>' % (n, ident, _inline(m.group(2), base), n))
            i += 1
            continue
        if ln.strip() in ("---", "***"):
            out.append("<hr>")
            i += 1
            continue
        if ln.startswith("|") and i + 1 < len(lines) and re.match(r"^\|[\s:|-]+\|$", lines[i + 1].strip()):
            head = [c.strip() for c in ln.strip().strip("|").split("|")]
            rows = []
            i += 2
            while i < len(lines) and lines[i].startswith("|"):
                rows.append([c.strip() for c in lines[i].strip().strip("|").split("|")])
                i += 1
            t = "<table><thead><tr>%s</tr></thead><tbody>" % "".join("<th>%s</th>" % _inline(h, base) for h in head)
            t += "".join("<tr>%s</tr>" % "".join("<td>%s</td>" % _inline(c, base) for c in r) for r in rows)
            out.append(t + "</tbody></table>")
            continue
        if ln.startswith(">"):
            q = []
            while i < len(lines) and lines[i].startswith(">"):
                q.append(lines[i][1:].strip())
                i += 1
            out.append("<blockquote>%s</blockquote>" % _inline(" ".join(q), base))
            continue
        if re.match(r"^\s*([-*]|\d+\.)\s+", ln):
            ordered = bool(re.match(r"^\s*\d+\.", ln))
            items = []
            while i < len(lines) and re.match(r"^\s*([-*]|\d+\.)\s+", lines[i]):
                item = re.sub(r"^\s*([-*]|\d+\.)\s+", "", lines[i])
                i += 1
                while i < len(lines) and lines[i].startswith("  ") and lines[i].strip():
                    item += " " + lines[i].strip()
                    i += 1
                items.append("<li>%s</li>" % _inline(item, base))
            tag = "ol" if ordered else "ul"
            out.append("<%s>%s</%s>" % (tag, "".join(items), tag))
            continue
        if ln.startswith("<details") or ln.startswith("</details") or ln.startswith("<summary"):
            out.append(ln)
            i += 1
            continue
        if not ln.strip():
            i += 1
            continue
        para = [ln]
        i += 1
        while i < len(lines) and lines[i].strip() and not re.match(r"^(#|```|\||>|\s*([-*]|\d+\.)\s|<details|</details)", lines[i]):
            para.append(lines[i])
            i += 1
        out.append("<p>%s</p>" % _inline(" ".join(p.strip() for p in para), base))
    return "\n".join(out)


def page(md_path, out_path=None):
    md_path = Path(md_path)
    md = md_path.read_text()
    m = re.search(r"^# (.+)", md, re.M)
    title = re.sub(r"[`*]", "", m.group(1)) if m else md_path.stem
    body = to_html(md, md_path.parent)
    out = Path(out_path or md_path.with_suffix(".html"))
    out.write_text("""<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>%s</title>
<style>%s</style>
</head>
<body>
<main>
%s
</main>
<footer>Generated from <code>%s</code>; edit the Markdown, not this file.</footer>
</body>
</html>
""" % (html.escape(title), CSS, body, html.escape(md_path.name)))
    return out
