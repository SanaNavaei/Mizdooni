import { useLogout } from "utils/logout";

function UserInfo() {
  const logout = useLogout();

  const email = localStorage.getItem('email');
  const city = localStorage.getItem('city');
  const country = localStorage.getItem('country');
  const role = localStorage.getItem('role');

  const text = (role === 'client') ? 'Your reservations are also emailed to' : 'Each restaurants new reservations are emailed to';

  const handleLogout = () => {
    logout(false);
  };

  return (
    <div className="alert px-3 rounded-3 d-sm-flex justify-content-between align-items-center">
      <p className="m-0">{text} <a className="miz-text-red text-decoration-none" href={`mailto:${email}`}>{email}</a>
      </p>
      <div className="d-flex align-items-center justify-content-between gap-2">
        {role === 'client' && (
          <p className="m-0">Address: {city || "City"}, {country || "Country"}</p>
        )}
        <button onClick={handleLogout} className="logout-button rounded-3 border-0 text-white px-3 py-1">Logout</button>
      </div>
    </div>
  );
}

export default UserInfo;
