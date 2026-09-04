import { useQuery } from '@tanstack/react-query'
import axios from 'axios'
import { Link } from 'react-router-dom'

import { useTheme } from '../features/theme/useTheme'
import type { HealthResponse } from '../types/api'

export function HomePage() {
  const { theme, resolvedTheme } = useTheme()
  const health = useQuery({
    queryKey: ['health'],
    queryFn: async () => {
      const { data } = await axios.get<HealthResponse>('/actuator/health')
      return data
    },
    retry: false,
  })

  return (
    <section className="max-w-xl space-y-6">
      <p className="font-mono text-xs tracking-[0.2em] text-accent uppercase">Phase 1 foundation</p>
      <h1 className="font-display text-4xl leading-tight tracking-tight">Portfolio shell is online.</h1>
      <p className="text-muted leading-relaxed">
        Routing, theme, and the application layout are wired. Portfolio sections are intentionally
        absent until later phases.
      </p>
      <dl className="grid gap-3 border border-border bg-surface p-4 text-sm">
        <div className="flex justify-between gap-4">
          <dt className="text-muted">Theme</dt>
          <dd className="font-mono">
            {theme} / {resolvedTheme}
          </dd>
        </div>
        <div className="flex justify-between gap-4">
          <dt className="text-muted">API health</dt>
          <dd className="font-mono">
            {health.isLoading ? 'checking' : health.data?.status ?? 'unreachable'}
          </dd>
        </div>
      </dl>
      <nav aria-label="Foundation routes" className="flex flex-wrap gap-4 text-sm">
        <Link className="underline decoration-border underline-offset-4 hover:decoration-accent" to="/projects/example">
          Case study route
        </Link>
        <Link className="underline decoration-border underline-offset-4 hover:decoration-accent" to="/resume">
          Resume route
        </Link>
      </nav>
    </section>
  )
}
