import { Link } from 'react-router-dom'

export function NotFoundPage() {
  return (
    <section className="max-w-xl space-y-4" aria-labelledby="not-found-heading">
      <p className="font-mono text-xs tracking-[0.2em] text-muted uppercase">404</p>
      <h1 id="not-found-heading" className="font-display text-3xl tracking-tight">
        Page not found
      </h1>
      <p className="text-muted leading-relaxed">
        The page you requested does not exist or has been moved.
      </p>
      <p>
        <Link className="underline decoration-border underline-offset-4 hover:decoration-accent" to="/">
          Return home
        </Link>
      </p>
    </section>
  )
}
