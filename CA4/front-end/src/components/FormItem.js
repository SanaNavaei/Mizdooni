function FormItem({ label, type, name, value, onChange, onBlur, error }) {
  const id = label.toLowerCase();

  return (
    <div className="mb-3">
      <label htmlFor={id} className="miz-text-red form-label">{label}</label>
      <input type={type} className={`form-control ${error ? 'auth-error' : ''}`} id={id} name={name} value={value} onChange={onChange} onBlur={onBlur} required />
      {error && <p className="miz-text-red py-1 fw-bold">{error}</p>}
    </div>
  );
}

export default FormItem;
