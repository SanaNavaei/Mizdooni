import { useState } from 'react';
import { useAuthContext } from 'components/AuthProvider';

function HeaderInfo() {
  const [button, setButton] = useState("Reserve Now!");
  const user = useAuthContext().user;

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
