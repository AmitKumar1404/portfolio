import { Link, useParams } from 'react-router-dom'

export function ProjectCaseStudyPage() {
  const { slug } = useParams()

  return (
    <article className="max-w-xl space-y-4">
      <p className="font-mono text-xs tracking-[0.2em] text-muted uppercase">Case study</p>
      <h1 className="font-display text-3xl tracking-tight">{slug}</h1>
      <p className="text-muted leading-relaxed">
        This route is reserved for project case studies. Content will be loaded from the API in a
        later phase.
      </p>
      <p>
        <Link className="underline decoration-border underline-offset-4 hover:decoration-accent" to="/">
          Back to home
        </Link>
      </p>
    </article>
  )
}
