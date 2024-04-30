function FormItem({ label, type }) {
  const id = label.toLowerCase();

  return (
    <div className="mb-3">
      <label htmlFor={id} className="miz-text-red form-label">{label}</label>
      <input type={type} className="form-control" id={id} name={id} required />
    </div>
  );
}

export default FormItem;
