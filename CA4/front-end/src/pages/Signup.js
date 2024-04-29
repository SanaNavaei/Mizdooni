import { Form } from 'react-router-dom';
import Logo from '../assets/images/logo.png';
import FormItem from '../components/FormItem';

function Signup() {
  return (
    <div class="min-vh-100 d-flex flex-column justify-content-center">
      <main class="container-fluid px-0 py-4">
        <div id="container" class="mx-auto px-4 px-sm-5 rounded-4">
          <div class="d-flex justify-content-between align-items-center pt-4">
            <h1 class="miz-text-red fs-2 m-0">Welcome to Mizdooni!</h1>
            <img src={Logo} alt="Mizdooni" height="96" width="120" />
          </div>
          <hr />
            <form class="px-3 px-sm-4 px-md-3 py-3">
              <FormItem label="username" type="text" />
              <FormItem label="password" type="password" />
              <FormItem label="email" type="email" />
              <FormItem label="country" type="text" />
              <FormItem label="city" type="text" />
              <div class="mb-5">
                <label for="role" class="miz-text-red form-label pe-2">Role:</label>
                <select class="form-select" id="role" name="role" required>
                  <option value="" disabled selected hidden>Choose a Role</option>
                  <option value="client">Client</option>
                  <option value="manager">Manager</option>
                </select>
              </div>
              <button type="submit" class="miz-button w-100 mb-3">Signup</button>
              <p class="bottom-text text-center">Already have an account? <a href="./login.html" class="miz-text-red text-decoration-none">Login here</a></p>
            </form>
        </div>
      </main>
    </div>
  );
}

export default Signup;
