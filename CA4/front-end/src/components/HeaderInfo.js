import { useState } from 'react';

function HeaderInfo() {
  const [button, setButton] = useState("Reserve Now!");
  const username = localStorage.getItem('username');

  return (
    <div className="d-flex align-items-center">
      {username && (
        <p className="m-0 pe-1 pe-sm-3 text-center">Welcome, {username}!</p>
      )}
      <button className="rounded-3 border-0 text-white">{button}</button>
    </div>
  );
}

export default HeaderInfo;
