function CustomerReserve({ time, name, table, seats, isCancel, isPastTime }) {
  let rowClass = '';
  if (isCancel) {
    rowClass += 'cancelled-reservation ';
  }
  if (isPastTime) {
    rowClass += 'past-reservation';
  }

  return (
    <tr className={rowClass}>
      <td className="ps-3">{time}</td>
      <td>{name}</td>
      <td>Table-{table}</td>
      <td>{seats} Seats</td>
      <td className="pe-3 text-end">
        {isCancel ? (
          <button className="miz-link-button" disabled>Canceled</button>
        ) : (
          <button className="miz-link-button">
            {isPastTime ? 'Add Comment' : 'Cancel'}
          </button>
        )}
      </td>
    </tr>
  );
}

export default CustomerReserve;
