export type HealthStatus = 'UP' | 'DOWN' | 'UNKNOWN'

export interface HealthResponse {
  status: HealthStatus
}
