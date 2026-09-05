export type HealthStatus = 'UP' | 'DOWN' | 'UNKNOWN'

export interface HealthResponse {
  status: HealthStatus
}

export type Availability = 'OPEN' | 'SELECTIVE' | 'UNAVAILABLE'

export interface ProfileResponse {
  name: string
  headline: string | null
  shortBio: string | null
  longBio: string | null
  location: string | null
  email: string | null
  githubUrl: string | null
  linkedinUrl: string | null
  websiteUrl: string | null
  resumeUrl: string | null
  avatarUrl: string | null
  availability: Availability
}

export interface ProjectResponse {
  slug: string
  title: string
  shortDescription: string
  featured: boolean
  displayOrder: number
  githubUrl: string | null
  liveUrl: string | null
}
