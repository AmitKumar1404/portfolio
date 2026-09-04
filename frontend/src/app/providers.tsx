import { QueryClientProvider } from '@tanstack/react-query'
import type { ReactNode } from 'react'

import { ThemeProvider } from '../features/theme/ThemeProvider'
import { queryClient } from '../lib/query'

export function AppProviders({ children }: { children: ReactNode }) {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider>{children}</ThemeProvider>
    </QueryClientProvider>
  )
}
