#!/usr/bin/env bash
# Narration clips for animation.html, from the course's videokit library.
set -euo pipefail
cd "$(dirname "$0")/.."
exec ../../tools/videokit/videokit.sh animation . "$@"
