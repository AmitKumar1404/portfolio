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

export type EmploymentType = 'FULL_TIME' | 'PART_TIME' | 'CONTRACT' | 'INTERNSHIP' | 'FREELANCE'

export interface ExperienceResponse {
  company: string
  role: string
  employmentType: EmploymentType
  location: string | null
  companyUrl: string | null
  startDate: string
  endDate: string | null
  current: boolean
  description: string | null
  highlights: string[]
  displayOrder: number
}

export interface SkillResponse {
  category: string
  slug: string
  displayOrder: number
  skills: string[]
}
