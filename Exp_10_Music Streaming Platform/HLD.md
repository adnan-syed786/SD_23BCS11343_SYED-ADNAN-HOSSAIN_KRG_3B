# High-Level Design (HLD) — Music Streaming Platform

## 1. Functional Requirements
- User can register, login, and manage their profile
- Users can search for songs, albums, and artists
- Users can play/stream songs
- Users can create and manage playlists
- Free tier: limited quality + ads; Premium tier: high quality, no ads

## 2. Non-Functional Requirements
- The app should load quickly (low latency)
- Should support many users at the same time (scalable)
- Music should not stop or buffer unnecessarily (high availability)
- User data and payments must be secure

## 3. System Architecture

```
[Client Apps]  →  [API Server]  →  [Database]
     ↓                                  ↓
  [CDN]                            [Cache (Redis)]
```

### Components

| Component | Role |
|-----------|------|
| **Client** | Mobile app / Web browser used by the user |
| **API Server** | Handles all requests — login, search, playback, playlists |
| **Database (PostgreSQL)** | Stores users, songs, playlists, subscriptions |
| **Cache (Redis)** | Stores frequently accessed data (sessions, popular songs) |
| **File Storage (S3)** | Stores actual audio files and album images |
| **CDN** | Delivers audio files fast to users globally |

## 4. Data Flow — Playing a Song

1. User clicks **Play** on a song
2. Client sends request to **API Server**
3. API Server checks if user is logged in and has access
4. API Server gets the song's file location from **Database**
5. API Server returns a **CDN link** to the audio file
6. Client streams the audio directly from **CDN**

## 5. Data Flow — Search

1. User types a song name
2. Client sends search request to **API Server**
3. API Server checks **Cache** — if result exists, return immediately
4. If not in cache, query the **Database**
5. Results are cached for future requests and sent back to user

## 6. CAP Theorem (Simple View)

- **User logins & payments** → Consistency is important (no double billing)
- **Song catalog & playlists** → Availability is more important (slight delay is okay)
- **Play counts** → Eventual consistency is fine (can update later)

## 7. Basic Capacity Estimates

| Item | Estimate |
|------|----------|
| Total users | 10 million |
| Daily active users | 1 million |
| Songs in catalog | 10 million |
| Storage per song (3 quality levels) | ~15 MB |
| Total storage for songs | ~150 TB |

---
*End of HLD*
