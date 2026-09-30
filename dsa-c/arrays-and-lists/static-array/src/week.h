/** The week kept in seven separate variables: act one's "before", with nothing to loop over. */
#ifndef WEEK_H
#define WEEK_H

typedef struct {
    int mon, tue, wed, thu, fri, sat, sun;
} WeekInVariables;

enum { HAND_WRITTEN_COMPARISONS = 6 };

int hottest(const WeekInVariables *w);     /* six comparisons, written out by hand */
int day(const WeekInVariables *w, int d);  /* a switch with a case for every day */

#endif
