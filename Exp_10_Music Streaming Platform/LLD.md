# Low-Level Design (LLD) — Music Streaming Platform

## 1. Main Classes

### User
```
User {
  id, username, email, passwordHash,
  subscriptionTier,   // FREE or PREMIUM
  country, createdAt
}
```

### Song (Track)
```
Song {
  id, title, artistId, albumId,
  duration,           // in seconds
  audioFileUrl,       // link to file on S3/CDN
  explicit,           // true/false
  playCount
}
```

### Artist
```
Artist {
  id, name, bio,
  imageUrl, genres[]
}
```

### Album
```
Album {
  id, title, artistId,
  releaseDate, coverImageUrl,
  songs[]             // list of song IDs
}
```

### Playlist
```
Playlist {
  id, name, ownerId,
  isPublic,           // true = anyone can view
  songs[],            // list of song IDs
  createdAt
}
```

### PlaySession
```
PlaySession {
  id, userId, songId,
  startTime, endTime,
  deviceType          // MOBILE / WEB
}
```

---

## 2. Database Schema (Key Tables)

### users
| Column | Type | Notes |
|--------|------|-------|
| id | UUID | Primary key |
| username | VARCHAR | Unique |
| email | VARCHAR | Unique |
| password_hash | VARCHAR | Encrypted |
| subscription_tier | VARCHAR | FREE / PREMIUM |
| created_at | TIMESTAMP | |

### songs
| Column | Type | Notes |
|--------|------|-------|
| id | UUID | Primary key |
| title | TEXT | |
| album_id | UUID | Foreign key → albums |
| duration_sec | INT | |
| audio_url | TEXT | S3/CDN link |
| play_count | BIGINT | |

### playlists
| Column | Type | Notes |
|--------|------|-------|
| id | UUID | Primary key |
| name | TEXT | |
| owner_id | UUID | Foreign key → users |
| is_public | BOOLEAN | |
| created_at | TIMESTAMP | |

### playlist_songs
| Column | Type | Notes |
|--------|------|-------|
| playlist_id | UUID | Foreign key → playlists |
| song_id | UUID | Foreign key → songs |
| position | INT | Order in playlist |

### play_sessions
| Column | Type | Notes |
|--------|------|-------|
| id | UUID | Primary key |
| user_id | UUID | Foreign key → users |
| song_id | UUID | Foreign key → songs |
| start_time | TIMESTAMP | |
| end_time | TIMESTAMP | |

---

## 3. Sequence Diagram — Play a Song

```
User → App → API Server → DB / Cache → CDN
  1. User clicks Play
  2. App sends: POST /api/play {songId}
  3. API Server checks login (JWT token)
  4. API Server fetches song URL from DB (or Cache)
  5. Returns signed CDN URL to App
  6. App streams audio from CDN
  7. API Server logs the play event
```

---

## 4. Design Patterns Used

| Pattern | Where Used | Why |
|---------|-----------|-----|
| **Repository Pattern** | UserRepository, SongRepository | Separate database logic from business logic |
| **Strategy Pattern** | Recommendation algorithms | Easily swap recommendation logic |
| **Observer Pattern** | Play events → Analytics | Notify analytics when a song is played |
| **Cache-Aside** | Song/User data | Check cache first, then DB |
| **Factory Pattern** | Audio stream handler | Create correct stream type (MP3/AAC) |

---

## 5. OOP Principles Applied

- **Encapsulation** — User class hides password details; only exposes needed methods
- **Single Responsibility** — PlaybackService only handles playback, not user management
- **Abstraction** — Clients call `playSong()` without knowing how CDN URLs are generated
- **Inheritance** — `PremiumUser` extends `User` with extra permissions

---
*End of LLD*
