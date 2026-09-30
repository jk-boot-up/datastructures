# YouTube — Doubly Linked List

Everything needed to publish `video/doubly-linked-list-explained.mp4`. Copy the fields straight out of this file.

Chapter timings come from the video's `.srt`; they are regenerated whenever the video is rebuilt.

## Title

```
Doubly Linked List in Java - Insertion, Deletion, Display Backward, Reverse
```

75 characters (YouTube shows about 60 before cutting a title off in search).

## Description

The first two lines are what a viewer sees above the fold, so they carry the hook rather than the boilerplate.

```
Doubly Linked List in Java, explained with a photo viewer that has Previous and Next buttons. We start with one-way pointers and see Previous from the last photo walk three steps from the start. Then we build the list by hand, as a C textbook does: every node holds data, a prev pointer and a next pointer, and the list keeps head and tail. We display it forward and backward, and count every textbook operation: insertAtBeginning and insertAtEnd with no walking, insertAtPosition setting four pointers, deleteAtEnd through the tail in O(1) where a singly list needs O(n), and deleteByKey with no prev pointer to keep. Then we forget one of the four pointers an insertion needs and watch the forward and backward walks disagree. We finish by reversing the list by swapping every node's prev and next, and with the bill: an extra pointer in every node.

CHAPTERS
00:00 Introduction
00:50 The Everyday Idea
01:16 The Words
01:41 Act One — A viewer with next pointers only
02:04 A viewer with next pointers only
02:13 Act Two — Pointers both ways, and both ends
02:38 Pointers both ways, and both ends
02:49 Act Three — Insertion and deletion at both ends and in the middle
03:18 Insertion and deletion at both ends and in the middle
03:44 Act Four — The pointer nobody set
04:10 The pointer nobody set
04:22 Act Five — Reversal, and the bill
04:48 Reversal, and the bill
04:55 Time and Space Complexity
05:26 Compared With Related Structures
05:55 Common Mistakes
06:19 Thanks for Watching

SOURCE CODE, WRITTEN NOTES AND AN INTERACTIVE ANIMATION
https://github.com/jk-boot-up/datastructures/tree/main/gradle-java/arrays-and-lists/doubly-linked-list

WHAT YOU NEED FIRST
Java basics: variables, loops, classes and methods. No data-structures knowledge is assumed; START-HERE.md in the repository explains memory, references and counting steps.
```

## Chapters

YouTube shows these as chapters because there are at least three and the first is at `00:00`.

```
00:00 Introduction
00:50 The Everyday Idea
01:16 The Words
01:41 Act One — A viewer with next pointers only
02:04 A viewer with next pointers only
02:13 Act Two — Pointers both ways, and both ends
02:38 Pointers both ways, and both ends
02:49 Act Three — Insertion and deletion at both ends and in the middle
03:18 Insertion and deletion at both ends and in the middle
03:44 Act Four — The pointer nobody set
04:10 The pointer nobody set
04:22 Act Five — Reversal, and the bill
04:48 Reversal, and the bill
04:55 Time and Space Complexity
05:26 Compared With Related Structures
05:55 Common Mistakes
06:19 Thanks for Watching
```

## Tags

```
doubly linked list, linked list java, prev next pointers, java linkedlist, linked list remove, data structures, java data structures, java, java 25, algorithms, computer science, programming tutorial, beginner, big o
```

216 characters, under YouTube's 500 limit.

## Thumbnail

![Thumbnail](thumbnail.png)

Upload `docs/thumbnail.png` (1280×720) under **Details → Thumbnail → Upload file**. It is made for the small size YouTube shows in search: the name, one line of promise and one piece of code.

## Upload checklist

- [ ] Upload `video/doubly-linked-list-explained.mp4` (1080p, H.264, 30 fps, AAC 48 kHz)
- [ ] Set the video language to **English**, then add subtitles: *Upload file → With timing* → `video/doubly-linked-list-explained.srt`
- [ ] Upload `docs/thumbnail.png` as the thumbnail
- [ ] Paste the title, description and tags from above
- [ ] Add to the **Data Structures in Java** playlist
- [ ] Wait for 1080p processing before sharing the link

## Runtime

07:02.
