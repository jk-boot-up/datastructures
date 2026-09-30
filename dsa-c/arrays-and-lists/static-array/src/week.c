/** Seven variables and no index: every question must name all seven days. */
#include "week.h"

int hottest(const WeekInVariables *w) {
    int max = w->mon;
    if (w->tue > max) max = w->tue;
    if (w->wed > max) max = w->wed;
    if (w->thu > max) max = w->thu;
    if (w->fri > max) max = w->fri;
    if (w->sat > max) max = w->sat;
    if (w->sun > max) max = w->sun;
    return max;
}

int day(const WeekInVariables *w, int d) {
    switch (d) {
        case 0: return w->mon;
        case 1: return w->tue;
        case 2: return w->wed;
        case 3: return w->thu;
        case 4: return w->fri;
        case 5: return w->sat;
        case 6: return w->sun;
        default: return 0;
    }
}
