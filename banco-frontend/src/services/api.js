import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Add token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authAPI = {
  register: (username, password) => api.post('/auth/register', { username, password }),
  login: (username, password) => api.post('/auth/login', { username, password }),
};

export const accountAPI = {
  getAll: () => api.get('/api/accounts'),
  create: (account) => api.post('/api/accounts', account),
  update: (id, account) => api.put(`/api/accounts/${id}`, account),
  delete: (id) => api.delete(`/api/accounts/${id}`),
  deposit: (id, amount) => api.post(`/api/accounts/${id}/deposit`, { amount }),
  withdraw: (id, amount) => api.post(`/api/accounts/${id}/withdraw`, { amount }),
  transfer: (fromId, toId, amount) => api.post('/api/accounts/transfer', { fromId, toId, amount }),
};

export default api;
