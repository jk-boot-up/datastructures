"""docs/thumbnail.png: the YouTube thumbnail, 1280x720.

Judged at about 360 pixels wide in a search result, so it carries only the
structure's name, one line of promise and one short piece of code, each large
enough to survive the shrink. Nothing is ever struck through.
"""

from PIL import Image, ImageDraw

from videokit.slides import theme as T

W, H = 1280, 720


def _fit(d, text, path, start, max_w, floor=40):
    size = start
    while size > floor and d.textlength(text, font=T.font(path, size)) > max_w:
        size -= 2
    return T.font(path, size)


def render(d_toml, out_path):
    p = d_toml["project"]
    img = Image.new("RGB", (W, H), T.POSTER_BOTTOM)
    g = ImageDraw.Draw(img)
    for y in range(H):
        t = y / (H - 1)
        g.line([(0, y), (W, y)], fill=tuple(int(a + (b - a) * t) for a, b in zip(T.POSTER_TOP, T.POSTER_BOTTOM)))
    d = ImageDraw.Draw(img)
    d.rectangle([0, 0, W, 10], fill=T.GOLD)
    d.text((64, 46), "C  ·  DATA STRUCTURES", font=T.font(T.SANS_B, 28), fill=T.GOLD)
    lines = p.get("thumb_lines") or [p["title"].upper()]
    y = 150
    for ln in lines:
        f = _fit(d, ln, T.SANS_B, 96 if len(lines) > 1 else 110, W - 128)
        d.text((64, y), ln, font=f, fill=T.TEXT)
        y += f.size + 8
    d.rectangle([64, y + 18, 260, y + 28], fill=T.GREEN)
    y += 64
    f = _fit(d, p["tagline"], T.SANS_B, 44, W - 128, 28)
    d.text((64, y), p["tagline"], font=f, fill=T.TEXT)
    y += f.size + 30
    code = p["thumb_code"]
    cf = _fit(d, code, T.MONO_B, 38, W - 220, 22)
    cw = int(d.textlength(code, font=cf)) + 56
    d.rounded_rectangle([64, y, 64 + cw, y + cf.size + 34], radius=16, fill=T.INK, outline=T.GREEN, width=4)
    d.text((64 + 28, y + 16), code, font=cf, fill=T.GREEN)
    nf = T.font(T.SANS_B, 30)
    nw = int(d.textlength(T.AUTHOR, font=nf)) + 48
    d.rounded_rectangle([W - 64 - nw, H - 94, W - 64, H - 40], radius=27, fill=T.INK, outline=T.CLIENT, width=3)
    d.text((W - 64 - nw // 2, H - 84), T.AUTHOR, font=nf, fill=T.CLIENT, anchor="ma")
    img.save(out_path)
    return out_path
