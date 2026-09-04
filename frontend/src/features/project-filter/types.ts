export type ProjectFilterState = {
  technology?: string
  featuredOnly: boolean
}

export const defaultProjectFilter: ProjectFilterState = {
  featuredOnly: false,
}
