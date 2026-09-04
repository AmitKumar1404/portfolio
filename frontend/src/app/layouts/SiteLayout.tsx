import { Outlet } from 'react-router-dom'

import { ThemeToggle } from '../../features/theme/ThemeToggle'

export function SiteLayout() {
  return (
    <div className="site-grid min-h-svh bg-background text-foreground">
      <a
        href="#main-content"
        className="sr-only focus:not-sr-only focus:absolute focus:left-4 focus:top-4 focus:z-50 focus:bg-surface focus:px-3 focus:py-2"
      >
        Skip to content
      </a>

      <header className="border-b border-border bg-background/85 backdrop-blur-sm">
        <div className="mx-auto flex h-14 w-full max-w-5xl items-center justify-between px-4">
          <p className="font-mono text-xs tracking-[0.18em] uppercase text-muted">Amit Kumar</p>
          <ThemeToggle />
        </div>
      </header>

      <main id="main-content" className="mx-auto w-full max-w-5xl px-4 py-16">
        <Outlet />
      </main>
    </div>
  )
}
