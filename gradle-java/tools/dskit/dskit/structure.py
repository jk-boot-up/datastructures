"""Draw a data structure's state as SVG, for the animation and the video.

One picture format serves both: the animation embeds the SVG inline (its
colours are --vk-* variables, so the page's Light / Dim / Dark themes recolour
it), and videokit's `structure` slide turns the same SVG into a PNG.

A picture is a dict, usually written in ds.toml:

    { type = "array", cells = [3, 8, 5, ""], size = 3, marks = { "1" = "hi" },
      pointers = [[3, "size"]], title = "capacity 4, size 3" }

Types: array, stack, linked, tree, graph, buckets, grid, plane, group.
Marks colour one part: hi (just changed, amber), ok (green), bad (red),
new (purple), cold (blue), dim (faded). A mark's key is an index, a label,
"r,c" for a grid, "b:i" for a bucket entry, or "a-b" for a graph edge.
"""

from html import escape

PAL = {
    "bg": "0f172a", "cell": "1e293b", "line": "475569", "text": "e2e8f0", "muted": "94a3b8",
    "hi": "fbbf24", "hi_bg": "2a2110", "ok": "34d399", "ok_bg": "10261f", "bad": "f87171",
    "bad_bg": "3f1d24", "new": "a78bfa", "new_bg": "1e1b4b", "cold": "38bdf8", "cold_bg": "0c2b3d",
}
FONT = "ui-sans-serif, -apple-system, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif"
MONO = "ui-monospace, 'SF Mono', Menlo, Consolas, monospace"


def c(name):
    h = PAL[name]
    return "var(--vk-%s, #%s)" % (h, h)


def _style(mark):
    """(fill, stroke, text colour, stroke width) for a mark."""
    if mark in ("hi", "ok", "bad", "new", "cold"):
        return c(mark + "_bg"), c(mark), c(mark), 3
    if mark == "dim":
        return "none", c("line"), c("muted"), 1.5
    return c("cell"), c("line"), c("text"), 2


def _mark(p, key):
    return (p.get("marks") or {}).get(str(key))


class Svg:
    def __init__(self):
        self.parts = []

    def rect(self, x, y, w, h, mark=None, r=8, dash=False):
        fill, stroke, _, sw = _style(mark)
        self.parts.append('<rect x="%g" y="%g" width="%g" height="%g" rx="%g" style="fill:%s;stroke:%s;'
                          'stroke-width:%g%s"/>' % (x, y, w, h, r, fill, stroke, sw,
                                                    ";stroke-dasharray:6 5" if dash else ""))

    def circle(self, x, y, r, mark=None, fill=None):
        f, stroke, _, sw = _style(mark)
        self.parts.append('<circle cx="%g" cy="%g" r="%g" style="fill:%s;stroke:%s;stroke-width:%g"/>'
                          % (x, y, r, fill or f, stroke, sw))

    def text(self, x, y, s, size=22, colour=None, mono=True, bold=False, anchor="middle"):
        self.parts.append('<text x="%g" y="%g" text-anchor="%s" dominant-baseline="central" style="'
                          'font-family:%s;font-size:%gpx;font-weight:%s;fill:%s">%s</text>'
                          % (x, y, anchor, MONO if mono else FONT, size, 700 if bold else 400,
                             colour or c("text"), escape(str(s))))

    def line(self, x1, y1, x2, y2, colour=None, width=2.5, dash=False, head=False, head2=False):
        colour = colour or c("muted")
        self.parts.append('<line x1="%g" y1="%g" x2="%g" y2="%g" style="stroke:%s;stroke-width:%g%s"/>'
                          % (x1, y1, x2, y2, colour, width, ";stroke-dasharray:6 5" if dash else ""))
        if head:
            self._head(x1, y1, x2, y2, colour)
        if head2:
            self._head(x2, y2, x1, y1, colour)

    def path(self, d, colour=None, width=2.5, head_at=None):
        colour = colour or c("muted")
        self.parts.append('<path d="%s" style="fill:none;stroke:%s;stroke-width:%g"/>' % (d, colour, width))
        if head_at:
            self._head(*head_at, colour)

    def _head(self, x1, y1, x2, y2, colour):
        import math
        a = math.atan2(y2 - y1, x2 - x1)
        L, W = 13, 6.5
        pts = [(x2, y2), (x2 - L * math.cos(a) + W * math.sin(a), y2 - L * math.sin(a) - W * math.cos(a)),
               (x2 - L * math.cos(a) - W * math.sin(a), y2 - L * math.sin(a) + W * math.cos(a))]
        self.parts.append('<polygon points="%s" style="fill:%s"/>'
                          % (" ".join("%g,%g" % p for p in pts), colour))

    def body(self):
        return "\n".join(self.parts)


# ------------------------------------------------------------ drawers ----
# Each drawer takes the picture and an origin, draws into svg, and returns the
# (width, height) it used, so a group can place several side by side.

CELL = 64


def draw_array(s, p, x0, y0):
    cells = p.get("cells", [])
    size = p.get("size", len(cells))
    longest = max((len(str(v)) for v in cells if v not in ("", None)), default=1)
    w = max(CELL * p.get("scale", 1), longest * 13 + 26)
    y = y0 + (28 if p.get("index", True) else 0)
    for i, v in enumerate(cells):
        x = x0 + i * w
        mark = _mark(p, i)
        empty = i >= size
        s.rect(x + 2, y, w - 4, CELL - 4, mark if not empty else (mark or "dim"), r=6, dash=empty and not mark)
        if v not in ("", None):
            s.text(x + w / 2, y + (CELL - 4) / 2, v, size=22 if len(str(v)) > 3 else 24,
                   colour=_style(mark)[2] if mark else (c("muted") if empty else c("text")))
        if p.get("index", True):
            s.text(x + w / 2, y0 + 12, p.get("first_index", 0) + i, size=15, colour=c("muted"))
    h = (y - y0) + CELL
    for idx, label in p.get("pointers", []):
        x = x0 + idx * w + w / 2
        s.line(x, h + y0 + 40, x, h + y0 + 8, colour=c("hi"), head=True)
        s.text(x, h + y0 + 56, label, size=17, colour=c("hi"), mono=False, bold=True)
    if p.get("pointers"):
        h += 70
    return max(len(cells), 1) * w, h


def draw_stack(s, p, x0, y0):
    cells = p.get("cells", [])            # bottom first
    w, ch = p.get("width", 200), 50
    label = p.get("top_label", "top")
    lw = max(60, len(label) * 10 + 24)    # room for the label left of the arrow
    n = max(len(cells), 1)
    top_y = y0 + 10
    for k, v in enumerate(reversed(cells)):
        i = len(cells) - 1 - k
        y = top_y + k * ch
        mark = _mark(p, i)
        s.rect(x0 + lw, y, w, ch - 6, mark, r=6)
        s.text(x0 + lw + w / 2, y + (ch - 6) / 2, v, size=20, colour=_style(mark)[2] if mark else None)
    if cells:
        s.line(x0 + lw - 10, top_y + (ch - 6) / 2, x0 + lw - 2, top_y + (ch - 6) / 2, colour=c("hi"), head=True)
        s.text(x0 + (lw - 14) / 2, top_y + (ch - 6) / 2, label, size=17, colour=c("hi"), mono=False, bold=True)
    base = top_y + n * ch
    s.line(x0 + lw - 10, base, x0 + lw + 10 + w, base, colour=c("line"), width=4)
    return w + lw + 20, base - y0 + 10


def draw_linked(s, p, x0, y0):
    """Boxes with arrows. Optional: double, circular, null_end, pointers above,
    loops = [[from, to]] for an arrow that goes back to an earlier box (or the same one),
    cut = [i, ...] to leave out the link after node i, and back = [i, ...] to draw the link between
    node i and node i + 1 pointing left (as in a list being reversed)."""
    nodes = p.get("nodes", [])
    double, circular = p.get("double"), p.get("circular")
    bh, gap = 56, 58
    widths = [max(92, len(str(v)) * 12 + 46) for v in nodes]
    xs, x = [], x0
    for w in widths:
        xs.append(x)
        x += w + gap
    loops = p.get("loops", [])
    cut = set(p.get("cut", []))       # no link drawn after these nodes
    back = set(p.get("back", []))     # the link after these nodes points backwards
    top = y0 + 40 + (46 if loops else 0)
    y = top
    for i, v in enumerate(nodes):
        bx, bw = xs[i], widths[i]
        mark = _mark(p, i)
        s.rect(bx, y, bw, bh, mark, r=10)
        s.text(bx + (bw - 26) / 2, y + bh / 2, v, size=20, colour=_style(mark)[2] if mark else None)
        s.line(bx + bw - 26, y + 6, bx + bw - 26, y + bh - 6, colour=c("line"), width=1.5)
        if i < len(nodes) - 1 and i not in cut:
            nx = xs[i + 1]
            if i in back:                     # the link points left: node i + 1's next is node i
                s.line(nx - 2, y + bh / 2, bx + bw - 12, y + bh / 2, head=True, colour=c("ok"))
            elif double:
                s.line(bx + bw - 12, y + bh / 2 - 9, nx - 2, y + bh / 2 - 9, head=True)
                s.line(nx, y + bh / 2 + 9, bx + bw + 2, y + bh / 2 + 9, head=True, colour=c("cold"))
            else:
                s.line(bx + bw - 12, y + bh / 2, nx - 2, y + bh / 2, head=True)
    end_x = (xs[-1] + widths[-1]) if nodes else x0
    if nodes and circular:
        s.path("M %g %g C %g %g, %g %g, %g %g" % (end_x - 12, y + bh, end_x + 30, y + bh + 80,
                                                   x0 - 30, y + bh + 80, x0 + widths[0] / 2, y + bh + 2),
               head_at=(x0 + widths[0] / 2 - 2, y + bh + 20, x0 + widths[0] / 2, y + bh + 2))
    elif nodes and p.get("null_end", True):
        s.line(end_x - 12, y + bh / 2, end_x + 34, y + bh / 2, head=True)
        s.text(end_x + 64, y + bh / 2, "null", size=17, colour=c("muted"))
        end_x += 90
    for loop in loops:
        f, t = loop[0], loop[1]
        colour = c(loop[2]) if len(loop) > 2 else c("bad")
        fx = xs[f] + widths[f] - 13
        tx = xs[t] + widths[t] / 2
        lift = 44
        s.path("M %g %g C %g %g, %g %g, %g %g" % (fx, y, fx + 10, y - lift, tx, y - lift, tx, y - 2),
               colour=colour, width=3, head_at=(tx, y - 16, tx, y - 2))
    for idx, label in p.get("pointers", []):
        px = xs[idx] + widths[idx] / 2
        s.line(px, y0 + 8, px, y0 + 32, colour=c("hi"), head=True)
        s.text(px, y0, label, size=16, colour=c("hi"), mono=False, bold=True)
    return max(end_x - x0, 92), (top - y0) + bh + (100 if circular else 20)


def _tree_layout(node, depth, pos, out):
    """In-order x positions, so a binary tree's left and right children stay on their sides."""
    if node is None:
        return
    if isinstance(node, (str, int, float)):
        node = [node]
    label, kids = node[0], list(node[1:])
    binary = len(kids) == 2 or any(k is None for k in kids)
    ids = []
    if binary:
        left, right = (kids + [None, None])[:2]
        li = _tree_layout(left, depth + 1, pos, out) if left is not None else None
        me = len(out)
        out.append(dict(label=label, x=pos[0], depth=depth, kids=[]))
        pos[0] += 1
        ri = _tree_layout(right, depth + 1, pos, out) if right is not None else None
        out[me]["kids"] = [k for k in (li, ri) if k is not None]
        return me
    first = len(out)
    kid_ids = [_tree_layout(k, depth + 1, pos, out) for k in kids]
    me = len(out)
    xs = [out[k]["x"] for k in kid_ids]
    out.append(dict(label=label, x=(sum(xs) / len(xs)) if xs else pos[0], depth=depth, kids=kid_ids))
    if not xs:
        pos[0] += 1
    return me


def draw_tree(s, p, x0, y0):
    out = []
    root = _tree_layout(p["tree"], 0, [0], out)
    if root is None:
        s.text(x0 + 80, y0 + 40, "(empty)", size=20, colour=c("muted"))
        return 160, 80
    dx, dy, r = p.get("dx", 64), p.get("dy", 92), p.get("r", 26)
    for n in out:
        n["cx"], n["cy"] = x0 + r + 8 + n["x"] * dx, y0 + r + 8 + n["depth"] * dy
    for n in out:
        for k in n["kids"]:
            m = out[k]
            s.line(n["cx"], n["cy"] + r, m["cx"], m["cy"] - r, colour=c("line"), width=2)
    for n in out:
        lab = n["label"]
        sub = None
        if isinstance(lab, str) and "|" in lab:
            lab, sub = lab.split("|", 1)
        mark = _mark(p, lab)
        colour = (p.get("colours") or {}).get(str(lab))
        fill = {"red": "var(--vk-3f1d24, #3f1d24)", "black": "var(--vk-020617, #020617)"}.get(colour)
        s.circle(n["cx"], n["cy"], r, mark, fill=fill if not mark else None)
        if colour == "red" and not mark:
            s.circle(n["cx"], n["cy"], r, "bad")
        s.text(n["cx"], n["cy"], lab, size=min(20, 90 / max(2, len(str(lab))) + 6),
               colour=_style(mark)[2] if mark else (c("bad") if colour == "red" else None))
        if sub:
            s.text(n["cx"] + r + 4, n["cy"] - r + 2, sub, size=13, colour=c("muted"), anchor="start")
    w = max(n["cx"] for n in out) - x0 + r + 40
    h = max(n["cy"] for n in out) - y0 + r + 12
    return w, h


def draw_graph(s, p, x0, y0):
    unit = p.get("unit", 90)
    pos = {str(n[0]): (x0 + 40 + n[1] * unit, y0 + 40 + n[2] * unit) for n in p["nodes"]}
    r = 28
    import math
    for e in p.get("edges", []):
        a, b = str(e[0]), str(e[1])
        (x1, y1), (x2, y2) = pos[a], pos[b]
        ang = math.atan2(y2 - y1, x2 - x1)
        sx, sy = x1 + r * math.cos(ang), y1 + r * math.sin(ang)
        ex, ey = x2 - r * math.cos(ang), y2 - r * math.sin(ang)
        mark = _mark(p, "%s-%s" % (a, b)) or (None if p.get("directed") else _mark(p, "%s-%s" % (b, a)))
        colour = {"hi": c("hi"), "ok": c("ok"), "bad": c("bad"), "new": c("new"), "dim": c("line")}.get(mark, c("muted"))
        s.line(sx, sy, ex, ey, colour=colour, width=4 if mark and mark != "dim" else 2.5,
               head=bool(p.get("directed")), dash=mark == "dim")
        if len(e) > 2 and e[2] not in ("", None):
            mx, my = (sx + ex) / 2, (sy + ey) / 2
            s.rect(mx - 17, my - 13, 34, 26, None, r=5)
            s.text(mx, my, e[2], size=15, colour=colour if mark else c("hi"))
    for n in p["nodes"]:
        x, y = pos[str(n[0])]
        mark = _mark(p, n[0])
        s.circle(x, y, r, mark)
        s.text(x, y, n[3] if len(n) > 3 else n[0], size=18, colour=_style(mark)[2] if mark else None)
    w = max(v[0] for v in pos.values()) - x0 + 70
    h = max(v[1] for v in pos.values()) - y0 + 70
    return w, h


def draw_buckets(s, p, x0, y0):
    rows = p["buckets"]
    bw, bh, gap = 110, 44, 36
    for b, chain in enumerate(rows):
        y = y0 + b * (bh + 12)
        s.rect(x0, y, 56, bh, _mark(p, b), r=5)
        s.text(x0 + 28, y + bh / 2, b, size=17, colour=c("muted"))
        x = x0 + 56
        if not chain:
            s.text(x + 30, y + bh / 2, p.get("empty", "·"), size=18, colour=c("line"))
        for i, v in enumerate(chain):
            s.line(x + 2, y + bh / 2, x + gap - 2, y + bh / 2, head=True)
            x += gap
            mark = _mark(p, "%d:%d" % (b, i))
            s.rect(x, y, bw, bh, mark, r=8)
            s.text(x + bw / 2, y + bh / 2, v, size=min(18, 160 / max(2, len(str(v))) + 4),
                   colour=_style(mark)[2] if mark else None)
            x += bw
    width = 56 + max((len(ch) for ch in rows), default=0) * (bw + gap) + 20
    return width, len(rows) * (bh + 12)


def draw_grid(s, p, x0, y0):
    rows = p["rows"]
    cw, ch = p.get("cell_w", 64), p.get("cell_h", 44)
    ox = x0 + (70 if p.get("row_labels") else 0)
    oy = y0 + (30 if p.get("col_labels") else 0)
    for j, lab in enumerate(p.get("col_labels", [])):
        s.text(ox + j * cw + cw / 2, y0 + 12, lab, size=15, colour=c("muted"))
    for i, row in enumerate(rows):
        if p.get("row_labels"):
            s.text(x0 + 60, oy + i * ch + ch / 2, p["row_labels"][i], size=15, colour=c("muted"), anchor="end")
        for j, v in enumerate(row):
            mark = _mark(p, "%d,%d" % (i, j))
            empty = v in ("", None, 0) and p.get("sparse")
            s.rect(ox + j * cw + 1, oy + i * ch + 1, cw - 2, ch - 2, mark or ("dim" if empty else None), r=3)
            if v not in ("", None):
                s.text(ox + j * cw + cw / 2, oy + i * ch + ch / 2, v, size=16,
                       colour=_style(mark)[2] if mark else (c("line") if empty else None))
    return (ox - x0) + len(rows[0]) * cw if rows else 100, (oy - y0) + len(rows) * ch


def draw_plane(s, p, x0, y0):
    """Points, boxes and split lines on a square, for k-d trees and quadtrees."""
    size = p.get("size", 420)
    span = p.get("span", 10)
    k = size / span
    X = lambda v: x0 + v * k
    Y = lambda v: y0 + size - v * k
    s.rect(x0, y0, size, size, None, r=4)
    for r_ in p.get("rects", []):
        x1, y1, x2, y2 = r_[:4]
        s.rect(X(x1), Y(y2), (x2 - x1) * k, (y2 - y1) * k, r_[4] if len(r_) > 4 else "dim", r=2,
               dash=not (len(r_) > 4))
    for ln in p.get("lines", []):
        x1, y1, x2, y2 = ln[:4]
        s.line(X(x1), Y(y1), X(x2), Y(y2), colour=c(ln[4]) if len(ln) > 4 else c("line"), width=2)
    for pt in p.get("points", []):
        x, y, lab = pt[0], pt[1], pt[2] if len(pt) > 2 else ""
        mark = _mark(p, lab)
        s.circle(X(x), Y(y), 9, mark or "new")
        if lab:
            s.text(X(x) + 14, Y(y) - 12, lab, size=15, colour=_style(mark)[2] if mark else c("text"), anchor="start")
    return size, size


GROUP_MAX_W = 1300   # wider than this side by side, and the items are stacked instead


def draw_group(s, p, x0, y0):
    """Several pictures side by side, or one above another when `vertical = true` or when side by
    side would be too wide to read at video size."""
    gap = p.get("gap", 70)
    sizes = [draw_any(Svg(), item, 0, 0) for item in p["items"]]     # measure first
    vertical = p.get("vertical", sum(w for w, _ in sizes) + gap * (len(sizes) - 1) > GROUP_MAX_W)
    x, y, w_all, h_all = x0, y0, 0, 0
    for item in p["items"]:
        w, hh = draw_any(s, item, x, y)
        if vertical:
            y += hh + 40
            w_all, h_all = max(w_all, w), y - y0 - 40
        else:
            x += w + gap
            w_all, h_all = x - x0 - gap, max(h_all, hh)
    return w_all, h_all


def draw_skiplist(s, p, x0, y0):
    """Stations as columns, levels as rows (top = highest express), a head column on the left.

    stations = names, heights = levels each stands on, marks = {name: mark},
    path = [[level, from, to], ...] drawn in amber: the hops a search takes ("head" for the head).
    """
    names, heights = p["stations"], p["heights"]
    top = max(heights) if heights else 1
    bw, bh = max(64, max((len(n) for n in names), default=4) * 11 + 18), 34
    gx, gy = bw + 22, bh + 30
    hx = x0 + 36                     # room on the left for the L1, L2 ... labels
    col = {"head": hx}
    for i, n in enumerate(names):
        col[n] = hx + 70 + i * gx
    rowy = {lv: y0 + (top - lv) * gy for lv in range(1, top + 1)}
    path = {(int(e[0]), e[1], e[2]) for e in p.get("path", [])}
    for lv in range(1, top + 1):
        y = rowy[lv]
        s.rect(hx, y, 52, bh, None, r=6)
        s.text(hx + 26, y + bh / 2, "H", size=15, colour=c("muted"))
        s.text(hx - 8, y + bh / 2, "L%d" % lv, size=13, colour=c("muted"), anchor="end")
        prev_name, prev_x = "head", hx + 52
        for i, n in enumerate(names):
            if heights[i] < lv:
                continue
            x = col[n]
            on = (lv, prev_name, n) in path
            s.line(prev_x + 2, y + bh / 2, x - 3, y + bh / 2, colour=c("hi") if on else c("muted"),
                   width=4 if on else 2, head=True)
            mark = _mark(p, n)
            s.rect(x, y, bw, bh, mark, r=6)
            s.text(x + bw / 2, y + bh / 2, n, size=15, colour=_style(mark)[2] if mark else None, mono=False)
            prev_name, prev_x = n, x + bw
    w = (col[names[-1]] + bw - x0) if names else 120
    return w, top * gy


DRAW = dict(array=draw_array, stack=draw_stack, linked=draw_linked, tree=draw_tree, graph=draw_graph,
            buckets=draw_buckets, grid=draw_grid, plane=draw_plane, group=draw_group,
            skiplist=draw_skiplist)


def draw_any(s, p, x0, y0):
    top = 0
    if p.get("title"):
        s.text(x0, y0 + 12, p["title"], size=18, colour=c("muted"), mono=False, bold=True, anchor="start")
        top = 36
    w, h = DRAW[p["type"]](s, p, x0, y0 + top)
    h += top
    if p.get("note"):
        s.text(x0, y0 + h + 22, p["note"], size=17, colour=c("hi"), mono=False, anchor="start")
        h += 44
    # Titles and notes are drawn from the left edge; make room for them too.
    for text, px in ((p.get("title"), 9.6), (p.get("note"), 9.0)):
        if text:
            w = max(w, len(str(text)) * px + 8)
    return w, h


def svg(picture, pad=24):
    """The picture as a standalone, scalable SVG string."""
    s = Svg()
    w, h = draw_any(s, picture, pad, pad)
    return ('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 %d %d" width="%d" height="%d" '
            'role="img" style="max-width:100%%;height:auto">%s</svg>'
            % (w + 2 * pad, h + 2 * pad, w + 2 * pad, h + 2 * pad, s.body()))
