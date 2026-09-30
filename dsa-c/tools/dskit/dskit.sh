#!/usr/bin/env bash
#
# dskit -- makes and builds the course's data-structure projects.
#
#   tools/dskit/dskit.sh build <slug>     everything for one project, and the index
#   tools/dskit/dskit.sh help             every command
#
# It runs in videokit's Python environment ($VIDEOKIT_HOME, default ~/.cache/videokit),
# which videokit.sh creates on first use.
set -euo pipefail
HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TOOLS="$(dirname "$HERE")"
"$TOOLS/videokit/videokit.sh" help >/dev/null          # sets the environment up if needed
export VIDEOKIT_HOME="${VIDEOKIT_HOME:-$HOME/.cache/videokit}"
export DYLD_FALLBACK_LIBRARY_PATH="${DYLD_FALLBACK_LIBRARY_PATH:+$DYLD_FALLBACK_LIBRARY_PATH:}/opt/homebrew/lib:/usr/local/lib"
PYTHONPATH="$HERE:$TOOLS/videokit${PYTHONPATH:+:$PYTHONPATH}" exec "$VIDEOKIT_HOME/venv/bin/python" -m dskit "$@"
