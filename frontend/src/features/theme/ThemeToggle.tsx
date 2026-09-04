import { Monitor, Moon, Sun } from 'lucide-react'

import { cn } from '../../lib/cn'
import type { Theme } from '../../types/theme'
import { useTheme } from './useTheme'

const options: { value: Theme; label: string; icon: typeof Sun }[] = [
  { value: 'light', label: 'Light', icon: Sun },
  { value: 'dark', label: 'Dark', icon: Moon },
  { value: 'system', label: 'System', icon: Monitor },
]

export function ThemeToggle() {
  const { theme, setTheme } = useTheme()

  return (
    <div
      role="radiogroup"
      aria-label="Color theme"
      className="inline-flex items-center gap-0.5 rounded-md border border-border bg-surface p-0.5"
    >
      {options.map(({ value, label, icon: Icon }) => {
        const selected = theme === value
        return (
          <button
            key={value}
            type="button"
            role="radio"
            aria-checked={selected}
            aria-label={label}
            onClick={() => setTheme(value)}
            className={cn(
              'inline-flex size-8 items-center justify-center rounded-[5px] text-muted transition-colors',
              selected ? 'bg-background text-foreground' : 'hover:text-foreground',
            )}
          >
            <Icon className="size-3.5" aria-hidden="true" />
          </button>
        )
      })}
    </div>
  )
}
