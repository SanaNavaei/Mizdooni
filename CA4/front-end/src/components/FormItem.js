function FormItem({ label, type }) {
  return (
    <div class="mb-3">
      <label for={label} class="miz-text-red form-label">{label}</label>
      <input type={type} class="form-control" id={label} name={label} required />
    </div>
  );
}

export default FormItem;
