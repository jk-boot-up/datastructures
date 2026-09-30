# YouTube — Circular Linked List

Everything needed to publish `video/circular-linked-list-explained.mp4`. Copy the fields straight out of this file.

Chapter timings come from the video's `.srt`; they are regenerated whenever the video is rebuilt.

## Title

```
Circular Linked List in Java - Insertion, Deletion and the Counting-Out Game
```

76 characters (YouTube shows about 60 before cutting a title off in search).

## Description

The first two lines are what a viewer sees above the fold, so they carry the hook rather than the boilerplate.

```
Circular Linked List in Java, explained with players taking turns round a board game. We start with the players in a straight line, where the turn falls off the end after the last player and a special case sends it back to the first. Then we join the ends by hand, as a C textbook does: the last node points back at the first, and the list keeps a single pointer, last, so the first node is last.next and both ends are one step away. We count the real cost of every textbook operation: turns with no special case, insertAtBeginning and insertAtEnd in O(1), insertAtPosition, deleteAtBeginning in one pointer change, deleteAtEnd walking round to the node before last, and deleteByKey. Then a loop waiting for null walks a thousand steps and never finds one, and a do-while traversal stops where it started. We finish with the counting-out game, every third player out until one is left.

CHAPTERS
00:00 Introduction
00:47 The Everyday Idea
01:16 The Words
01:37 Act One — Players in a straight line
01:56 Players in a straight line
02:05 Act Two — Join the ends: last.next is the first node
02:27 Join the ends: last.next is the first node
02:34 Act Three — Turns, insertion and deletion
03:03 Turns, insertion and deletion
03:16 Act Four — Waiting for null
03:37 Waiting for null
03:43 Act Five — Counting out, and the bill
04:09 Counting out, and the bill
04:19 Time and Space Complexity
04:46 Compared With Related Structures
05:11 Common Mistakes
05:32 Thanks for Watching

SOURCE CODE, WRITTEN NOTES AND AN INTERACTIVE ANIMATION
https://github.com/jk-boot-up/datastructures/tree/main/gradle-java/arrays-and-lists/circular-linked-list

WHAT YOU NEED FIRST
Java basics: variables, loops, classes and methods. No data-structures knowledge is assumed; START-HERE.md in the repository explains memory, references and counting steps.
```

## Chapters

YouTube shows these as chapters because there are at least three and the first is at `00:00`.

```
00:00 Introduction
00:47 The Everyday Idea
01:16 The Words
01:37 Act One — Players in a straight line
01:56 Players in a straight line
02:05 Act Two — Join the ends: last.next is the first node
02:27 Join the ends: last.next is the first node
02:34 Act Three — Turns, insertion and deletion
03:03 Turns, insertion and deletion
03:16 Act Four — Waiting for null
03:37 Waiting for null
03:43 Act Five — Counting out, and the bill
04:09 Counting out, and the bill
04:19 Time and Space Complexity
04:46 Compared With Related Structures
05:11 Common Mistakes
05:32 Thanks for Watching
```

## Tags

```
circular linked list, linked list java, round robin, josephus problem, circular list buffer, data structures, java data structures, java, java 25, algorithms, computer science, programming tutorial, beginner, big o
```

214 characters, under YouTube's 500 limit.

## Thumbnail

![Thumbnail](thumbnail.png)

Upload `docs/thumbnail.png` (1280×720) under **Details → Thumbnail → Upload file**. It is made for the small size YouTube shows in search: the name, one line of promise and one piece of code.

## Upload checklist

- [ ] Upload `video/circular-linked-list-explained.mp4` (1080p, H.264, 30 fps, AAC 48 kHz)
- [ ] Set the video language to **English**, then add subtitles: *Upload file → With timing* → `video/circular-linked-list-explained.srt`
- [ ] Upload `docs/thumbnail.png` as the thumbnail
- [ ] Paste the title, description and tags from above
- [ ] Add to the **Data Structures in Java** playlist
- [ ] Wait for 1080p processing before sharing the link

## Runtime

06:17.
