import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { accountAPI } from '../services/api';
import './Dashboard.css';

const Dashboard = () => {
  const [accounts, setAccounts] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const response = await accountAPI.getAll();
        setAccounts(response.data);
      } catch (err) {
        setError('Error al cargar cuentas');
      }
    };
    fetchAccounts();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Dashboard de Banco</h1>
        <button onClick={handleLogout} className="logout-btn">Cerrar Sesión</button>
      </header>
      <main>
        {error && <p className="error">{error}</p>}
        <h2>Tus Cuentas</h2>
        <div className="accounts-grid">
          {accounts.map(account => (
            <div key={account.id} className="account-card">
              <h3>{account.accountNumber}</h3>
              <p>Propietario: {account.ownerName}</p>
              <p>Moneda: {account.currency}</p>
              <p>Saldo: {account.balance}</p>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
