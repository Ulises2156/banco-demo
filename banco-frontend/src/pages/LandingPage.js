import React from 'react';
import { Link } from 'react-router-dom';
import './LandingPage.css';

const LandingPage = () => {
  return (
    <div className="landing-page">
      <header className="hero">
        <h1>Bienvenido a Banco Demo</h1>
        <p>Tu banco seguro y confiable para todas tus necesidades financieras.</p>
        <div className="buttons">
          <Link to="/login" className="btn btn-primary">Iniciar Sesión</Link>
          <Link to="/register" className="btn btn-secondary">Registrarse</Link>
        </div>
      </header>
      <section className="features">
        <h2>Nuestros Servicios</h2>
        <div className="feature-grid">
          <div className="feature">
            <h3>Cuentas Seguras</h3>
            <p>Administra tus cuentas con la máxima seguridad.</p>
          </div>
          <div className="feature">
            <h3>Transferencias Rápidas</h3>
            <p>Realiza transferencias instantáneas entre cuentas.</p>
          </div>
          <div className="feature">
            <h3>Depósitos y Retiros</h3>
            <p>Gestiona tus fondos fácilmente.</p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default LandingPage;
