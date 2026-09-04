import { Link } from 'react-router-dom'

export function ResumePage() {
  return (
    <article className="max-w-xl space-y-4">
      <p className="font-mono text-xs tracking-[0.2em] text-muted uppercase">Resume</p>
      <h1 className="font-display text-3xl tracking-tight">Resume</h1>
      <p className="text-muted leading-relaxed">
        The printable resume view and PDF download will be added after the experience API is in
        place.
      </p>
      <p>
        <Link className="underline decoration-border underline-offset-4 hover:decoration-accent" to="/">
          Back to home
        </Link>
      </p>
    </article>
  )
}
