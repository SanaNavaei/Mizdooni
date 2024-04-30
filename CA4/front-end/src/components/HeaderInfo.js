import { useState } from 'react';

function HeaderInfo() {
  const [user, setUser] = useState("");
  const [button, setButton] = useState("Reserve Now!");

  return (
    <div className="d-flex align-items-center">
      {user && (
        <p className="m-0 pe-1 pe-sm-3 text-center">Welcome, {user}!</p>
      )}
      <button className="rounded-3 border-0 text-white">{button}</button>
    </div>
  );
}

export default HeaderInfo;
