import { useEffect } from 'react';

import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/authentication.css';

function Signup() {
  useEffect(() => {
    document.title = 'Signup';
  }, []);

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background-auth">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome to Mizdooni!" />
          <form className="px-3 px-sm-4 px-md-3 py-3">
            <FormItem label="Username" type="text" />
            <FormItem label="Password" type="password" />
            <FormItem label="Email" type="email" />
            <FormItem label="Country" type="text" />
            <FormItem label="City" type="text" />
            <div className="mb-5">
              <label htmlFor="role" className="miz-text-red form-label pe-2">Role:</label>
              <select className="form-select" id="role" name="role" defaultValue="" required>
                <option value="" disabled hidden>Choose a Role</option>
                <option value="client">Client</option>
                <option value="manager">Manager</option>
              </select>
            </div>
            <button type="submit" className="miz-button w-100 mb-3">Signup</button>
            <p className="bottom-text text-center">Already have an account? <a href="./login.html" className="miz-text-red text-decoration-none">Login here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Signup;
