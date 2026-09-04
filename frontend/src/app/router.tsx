import { createBrowserRouter } from 'react-router-dom'

import { HomePage } from '../pages/HomePage'
import { NotFoundPage } from '../pages/NotFoundPage'
import { ProjectCaseStudyPage } from '../pages/ProjectCaseStudyPage'
import { ResumePage } from '../pages/ResumePage'
import { SiteLayout } from './layouts/SiteLayout'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <SiteLayout />,
    children: [
      { index: true, element: <HomePage /> },
      { path: 'projects/:slug', element: <ProjectCaseStudyPage /> },
      { path: 'resume', element: <ResumePage /> },
      { path: '*', element: <NotFoundPage /> },
    ],
  },
])
