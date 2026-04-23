# API Design — Music Streaming Platform

## General Rules
- All APIs use: `https://api.musicapp.com/v1/...`
- Protected APIs require: `Authorization: Bearer <token>` in the header
- All responses are in **JSON format**

---

## 1. User Authentication

### Register
- **POST** `/v1/auth/register`
- **Body:**
```json
{ "username": "john", "email": "john@example.com", "password": "pass123" }
```
- **Response (201):**
```json
{ "userId": "u_123", "message": "Registration successful" }
```

### Login
- **POST** `/v1/auth/login`
- **Body:**
```json
{ "email": "john@example.com", "password": "pass123" }
```
- **Response (200):**
```json
{ "accessToken": "eyJhbG...", "expiresIn": 3600 }
```

---

## 2. Search

### Search Songs / Artists / Albums
- **GET** `/v1/search?q=Bohemian&type=track`
- **Query Params:**
  - `q` — search keyword (required)
  - `type` — `track`, `artist`, or `album`
- **Response (200):**
```json
{
  "tracks": [
    { "id": "t_001", "title": "Bohemian Rhapsody", "artist": "Queen", "duration": 354 }
  ]
}
```

---

## 3. Play a Song

### Start Playback
- **POST** `/v1/playback/play`
- **Headers:** `Authorization: Bearer <token>`
- **Body:**
```json
{ "songId": "t_001" }
```
- **Response (200):**
```json
{
  "streamUrl": "https://cdn.musicapp.com/songs/t_001.mp3?token=abc",
  "expiresAt": "2026-04-24T10:00:00Z"
}
```
> The client uses the `streamUrl` to play audio directly from CDN.

### Stop Playback
- **POST** `/v1/playback/stop`
- **Body:** `{ "songId": "t_001", "playedSeconds": 180 }`
- **Response (204):** No content

---

## 4. Playlists

### Create Playlist
- **POST** `/v1/playlists`
- **Body:**
```json
{ "name": "My Favourites", "isPublic": true }
```
- **Response (201):**
```json
{ "playlistId": "pl_abc", "name": "My Favourites" }
```

### Add Song to Playlist
- **POST** `/v1/playlists/{playlistId}/songs`
- **Body:** `{ "songId": "t_001" }`
- **Response (200):** Updated playlist

### Get Playlist
- **GET** `/v1/playlists/{playlistId}`
- **Response (200):**
```json
{
  "id": "pl_abc",
  "name": "My Favourites",
  "songs": [
    { "id": "t_001", "title": "Bohemian Rhapsody", "artist": "Queen" }
  ]
}
```

---

## 5. User Library

### Save a Song
- **POST** `/v1/me/songs`
- **Body:** `{ "songId": "t_001" }`
- **Response (200):** `{ "message": "Song saved" }`

### Get Saved Songs
- **GET** `/v1/me/songs`
- **Response (200):**
```json
{
  "songs": [
    { "id": "t_001", "title": "Bohemian Rhapsody", "savedAt": "2026-04-20" }
  ]
}
```

---

## 6. Subscription

### Get Subscription Plans
- **GET** `/v1/subscriptions/plans`
- **Response (200):**
```json
{
  "plans": [
    { "id": "free", "price": 0, "features": ["Ads", "160kbps audio"] },
    { "id": "premium", "price": 9.99, "features": ["No ads", "320kbps", "Offline"] }
  ]
}
```

### Subscribe to a Plan
- **POST** `/v1/subscriptions/subscribe`
- **Body:** `{ "planId": "premium", "paymentMethodId": "pm_123" }`
- **Response (201):**
```json
{ "subscriptionId": "sub_001", "status": "ACTIVE", "renewsOn": "2026-05-24" }
```

---

## Common HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (delete/stop) |
| 400 | Bad Request (invalid input) |
| 401 | Unauthorized (not logged in) |
| 403 | Forbidden (no permission) |
| 404 | Not Found |
| 429 | Too Many Requests |
| 500 | Server Error |

---

## Rate Limiting
- Free users: **100 requests/minute**
- Premium users: **200 requests/minute**
- If exceeded, server returns `429 Too Many Requests`

---
*End of API Spec*
