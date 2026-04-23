# Scaling — Music Streaming Platform

## Overview
As the number of users grows, the system must handle more requests without slowing down or crashing. Here are the key strategies used.

---

## 1. Load Balancing

**What it is:** Distributes incoming requests across multiple servers so no single server gets overloaded.

**How it works:**
- A **Load Balancer** sits in front of all API servers
- User requests are routed to the least busy server
- If one server goes down, the load balancer sends traffic to others automatically

```
Users → [Load Balancer] → [Server 1]
                        → [Server 2]
                        → [Server 3]
```

**Benefit:** Higher availability, no single point of failure.

---

## 2. Caching

**What it is:** Storing frequently accessed data in fast memory (Redis) so we don't hit the database every time.

**What we cache:**
| Data | Cache Duration |
|------|---------------|
| User login sessions | 15 minutes |
| Popular song metadata | 10 minutes |
| Search results | 5 minutes |
| User's playlists | 60 seconds |

**How it works (Cache-Aside pattern):**
1. Check Redis first
2. If data found → return it (fast!)
3. If not found → query the database, then store in Redis

**Benefit:** Reduces database load, speeds up response times.

---

## 3. CDN (Content Delivery Network)

**What it is:** A network of servers spread around the world that store copies of audio files and images closer to the user.

**How it works:**
- Song files are uploaded once to cloud storage (S3)
- CDN copies them to servers in different countries
- When a user in India plays a song, they get it from a nearby CDN server, not a distant data centre

**Benefit:** Faster streaming, less buffering, lower latency globally.

---

## 4. Database Scaling

**Vertical Scaling (Scale Up):** Use a more powerful database server (more RAM, CPU). Simple but has limits.

**Horizontal Scaling (Scale Out):**
- **Read Replicas** — Copy the database to multiple servers. Reads (search, browse) go to replicas; writes (new user, new playlist) go to the main DB.
- **Sharding** — Split the database by user ID or region so each shard handles a smaller portion of data.

```
            [Primary DB] ← Writes
                ↓
[Replica 1] [Replica 2] [Replica 3] ← Reads
```

**Benefit:** Handles more users without slowing down.

---

## 5. Summary Table

| Strategy | Problem Solved | Tool Used |
|----------|---------------|-----------|
| Load Balancing | Too many requests on one server | Nginx / AWS ELB |
| Caching | Slow repeated DB queries | Redis |
| CDN | Slow audio delivery to far users | AWS CloudFront |
| Read Replicas | Too many read queries on one DB | PostgreSQL Replicas |
| Auto-scaling | Traffic spikes (e.g., new album drop) | Kubernetes / AWS Auto Scaling |

---
*End of Scaling Doc*
