function RestaurantReservations({ reservations }) {
  let selectTableText = "fw-light text-end fs-7 m-0 ";
  let dateSelect = "miz-text-grey form-control w-75 rounded-3 text-start p-1 mb-2 "
  if (reservations.length === 0) {
    selectTableText += "miz-text-red";
    dateSelect += "d-none";
  } else {
    selectTableText += "miz-text-grey";
  }
  return (
    <section id="reservations-manager" className="col-lg-4 h-100 overflow-auto bg-white px-0">
      <div className="d-flex justify-content-between align-items-center mx-3 py-2">
        <h2 className="fw-semibold fs-6 m-0">Reservation List</h2>
        <div className="d-flex flex-column align-items-end">
          <input className={dateSelect} type="date" name="date" id="date" />
          <p id="select-table-text" className={selectTableText}>
            {reservations.length === 0 ? 'No Reservations.' : 'Select a table to see its reservations'}
          </p>
        </div>
      </div>
      <table id="reservations-table" className="table align-middle custom-border m-0">
        <tbody className="fw-light">
          {reservations.map((reservation, index) => (
            <tr key={index} className={reservation.cancelled ? 'text-decoration-line-through' : ''}>
              <td className="custom-border col-1">{reservation.time}</td>
              <td className="col-1">By {reservation.bookedBy}</td>
              <td className="text-center col-1 col-sm-2 col-lg-1 col-xxl-2">{`Table-${reservation.table}`}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}

export default RestaurantReservations;