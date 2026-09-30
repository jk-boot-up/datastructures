"""The pictures in START-HERE.md, drawn with the same renderer as every project."""

from .generate import ROOT, png

PICTURES = {
    "memory-row": {"type": "array", "cells": [21, 23, 19, 25, 24, 22, 20],
                   "marks": {"3": "hi"}, "pointers": [[3, "box 3"]],
                   "title": "Seven boxes in a row. Box 3 holds 25; the computer jumps straight to it."},
    "reference": {"type": "linked", "nodes": ["Ann", "Ben", "Cat"], "pointers": [[0, "first"]],
                  "title": "Each box holds a value and an arrow (a pointer) to the next box."},
    "look-at-every": {"type": "array", "cells": [4, 9, 2, 7, 5, 8, 1, 6],
                      "marks": {"0": "dim", "1": "dim", "2": "dim", "3": "dim", "4": "hi"},
                      "title": "Looking for 5, one box at a time: five looks. With a million boxes, up to a million."},
    "halving": {"type": "array", "cells": [1, 2, 4, 5, 6, 7, 8, 9],
                "marks": {"0": "dim", "1": "dim", "2": "dim", "3": "ok", "4": "dim", "5": "dim", "6": "dim", "7": "dim"},
                "pointers": [[3, "found in 3 looks"]],
                "title": "Sorted boxes: look in the middle, throw away half, repeat. A million boxes: about 20 looks."},
}


def draw():
    out = ROOT / "images"
    out.mkdir(exist_ok=True)
    for name, pic in PICTURES.items():
        png(pic, out / ("%s.png" % name), width=1100)
    return len(PICTURES)
