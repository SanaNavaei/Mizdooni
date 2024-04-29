function FormItem({ label, type }) {
  return (
    <div className="mb-3">
      <label for={label} className="miz-text-red form-label">{label}</label>
      <input type={type} className="form-control" id={label} name={label} required />
    </div>
  );
}

export default FormItem;
