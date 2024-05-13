import Logo from 'assets/images/logo.png';

function AuthenticationHeader({ text }) {
  return (
    <div>
      <div className="d-flex justify-content-between align-items-center pt-4">
        <h1 className="miz-text-red fs-2 m-0">{text}</h1>
        <img src={Logo} alt="Mizdooni" height="96" width="120" />
      </div>
      <hr />
    </div>
  );
}

export default AuthenticationHeader;
