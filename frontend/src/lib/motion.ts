import type { Transition } from 'framer-motion'

export const defaultTransition: Transition = {
  duration: 0.2,
  ease: [0.22, 1, 0.36, 1],
}

export const reducedMotionTransition: Transition = {
  duration: 0,
}
