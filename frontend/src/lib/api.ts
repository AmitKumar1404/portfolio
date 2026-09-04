import axios from 'axios'

const baseURL = import.meta.env.VITE_API_BASE_URL ?? '/api/v1'

export const api = axios.create({
  baseURL,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
  timeout: 15_000,
})
