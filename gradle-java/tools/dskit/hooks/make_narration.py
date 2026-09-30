#!/usr/bin/env python3
"""Called by videokit's docs stage with a project slug: runs dskit.publish.narration."""
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
sys.path.insert(0, str(Path(__file__).resolve().parents[2] / "videokit"))
from dskit import publish  # noqa: E402

for slug in [a for a in sys.argv[1:] if not a.startswith("--")]:
    publish.narration(slug)
    print("make_narration: " + slug)
