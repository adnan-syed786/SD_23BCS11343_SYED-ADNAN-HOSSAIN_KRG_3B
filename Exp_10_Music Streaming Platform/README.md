# Music Streaming Platform — Mini Project

## Overview
A basic music streaming web application where users can register, search for songs, play music, and manage playlists — similar to a simplified version of Spotify.

## Features
- User registration and login
- Search songs, albums, and artists
- Stream/play songs online
- Create and manage playlists
- Save favourite songs to library
- Free and Premium subscription tiers

## Tech Stack
| Layer | Technology |
|-------|-----------|
| Frontend | React.js |
| Backend | Node.js / Express |
| Database | PostgreSQL |
| Cache | Redis |
| File Storage | AWS S3 / Cloud Storage |
| Content Delivery | CDN (CloudFront) |

## Project Files
| File | Description |
|------|-------------|
| `HLD.md` | High-level system architecture |
| `LLD.md` | Classes, database schema, sequence flow |
| `api.md` | REST API endpoints |
| `scaling.md` | How the system handles growth |

## Assumptions
- Users stream music over the internet (no local storage by default)
- Music files are stored on cloud (S3) and served via CDN
- Free users see ads; Premium users get ad-free, high-quality audio
- Platform runs on cloud infrastructure

---
*Mini Project Submission — Music Streaming Platform*
