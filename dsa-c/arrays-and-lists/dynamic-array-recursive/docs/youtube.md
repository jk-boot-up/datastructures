# YouTube — Dynamic Array (Recursive)

Everything needed to publish `video/dynamic-array-recursive-explained.mp4`. Copy the fields straight out of this file.

Chapter timings come from the video's `.srt`; they are regenerated whenever the video is rebuilt.

## Title

```
Dynamic Array in C, Recursively - Recursive Resize and the Call Stack
```

69 characters (YouTube shows about 60 before cutting a title off in search).

## Description

The first two lines are what a viewer sees above the fold, so they carry the hook rather than the boilerplate.

```
Dynamic Array in C, written recursively, explained with a music playlist that grows one song at a time. We trace a recursive resize call by call: copy_from(0) copies Blue Sky and calls copy_from(1), down to the base case copy_from(4), four copies at depth 4. We grow the playlist to 9 songs with 2 resizes and 12 copies, traverse it at depth 9, insert at the front with 9 recursive shifts, delete from the middle with 4, and search recursively. We shrink to fit with 9 copies at depth 9 and see the capacity halve when deletions leave it a quarter full. We finish at the limit: appending a million songs overflows the call stack inside a resize, and the child process running it is stopped, because a recursive copy needs one frame per element. The loop version is in the Dynamic Array project.

CHAPTERS
00:00 Introduction
00:47 The Everyday Idea
01:25 The Words
01:53 Act One — A recursive copy
02:24 A recursive copy
02:39 Act Two — Growing
03:06 Growing
03:19 Act Three — The middle, recursively
03:44 The middle, recursively
03:57 Act Four — Shrinking, recursively
04:22 Shrinking, recursively
04:34 Act Five — The limit of recursion
05:07 The limit of recursion
05:18 Time and Space Complexity
05:48 Recursive or Loop?
06:09 Common Mistakes
06:33 Thanks for Watching

SOURCE CODE, WRITTEN NOTES AND AN INTERACTIVE ANIMATION
https://github.com/jk-boot-up/datastructures/tree/main/dsa-c/arrays-and-lists/dynamic-array-recursive

WHAT YOU NEED FIRST
C basics: variables, loops, functions, structs and pointers. No data-structures knowledge is assumed; START-HERE.md in the repository explains memory, pointers and counting steps.
```

## Chapters

YouTube shows these as chapters because there are at least three and the first is at `00:00`.

```
00:00 Introduction
00:47 The Everyday Idea
01:25 The Words
01:53 Act One — A recursive copy
02:24 A recursive copy
02:39 Act Two — Growing
03:06 Growing
03:19 Act Three — The middle, recursively
03:44 The middle, recursively
03:57 Act Four — Shrinking, recursively
04:22 Shrinking, recursively
04:34 Act Five — The limit of recursion
05:07 The limit of recursion
05:18 Time and Space Complexity
05:48 Recursive or Loop?
06:09 Common Mistakes
06:33 Thanks for Watching
```

## Tags

```
dynamic array, recursion, resize, call stack, stack overflow, amortized, data structures, c data structures, c programming, c17, algorithms, computer science, programming tutorial, beginner, big o
```

196 characters, under YouTube's 500 limit.

## Thumbnail

![Thumbnail](thumbnail.png)

Upload `docs/thumbnail.png` (1280×720) under **Details → Thumbnail → Upload file**. It is made for the small size YouTube shows in search: the name, one line of promise and one piece of code.

## Upload checklist

- [ ] Upload `video/dynamic-array-recursive-explained.mp4` (1080p, H.264, 30 fps, AAC 48 kHz)
- [ ] Set the video language to **English**, then add subtitles: *Upload file → With timing* → `video/dynamic-array-recursive-explained.srt`
- [ ] Upload `docs/thumbnail.png` as the thumbnail
- [ ] Paste the title, description and tags from above
- [ ] Add to the **Data Structures in C** playlist
- [ ] Wait for 1080p processing before sharing the link

## Runtime

07:22.
