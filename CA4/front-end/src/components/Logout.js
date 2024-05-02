function Logout({ email, country, city }) {
  return (
    <div className="alert px-3 rounded-3 d-sm-flex justify-content-between align-items-center">
      <p className="m-0">Your reservations are also emailed to <a className="miz-text-red text-decoration-none" href={`mailto:${email}`}>{email}</a>
      </p>
      <div className="d-flex align-items-center justify-content-between gap-2">
        {city && country && (
          <p className="m-0">Address: {city}, {country}</p>
        )}
        <button className="logout-button rounded-3 border-0 text-white px-3 py-1">Logout</button>
      </div>
    </div>
  );
}

export default Logout;
