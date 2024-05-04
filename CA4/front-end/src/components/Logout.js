function Logout({ country, city }) {
  const email = localStorage.getItem('email');

  const handleSubmit = (event) => {
    event.preventDefault();
    localStorage.clear();
    window.location.href = '/login';
  };

  return (
    <div className="alert px-3 rounded-3 d-sm-flex justify-content-between align-items-center">
      <p className="m-0">Your reservations are also emailed to <a className="miz-text-red text-decoration-none" href={`mailto:${email}`}>{email}</a>
      </p>
      <div className="d-flex align-items-center justify-content-between gap-2">
        {city && country && (
          <p className="m-0">Address: {city}, {country}</p>
        )}
        <form onSubmit={handleSubmit}>
          <button type="submit" className="logout-button rounded-3 border-0 text-white px-3 py-1">Logout</button>
        </form>
      </div>
    </div>
  );
}

export default Logout;
