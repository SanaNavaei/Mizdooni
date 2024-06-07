import FullStar from 'assets/icons/star_filled.svg';
import EmptyStar from 'assets/icons/star_empty.svg';

function Stars({ count, total = 5, size = 12 }) {
  return (
    <>
      {[...Array(count)].map((_, index) => (
        <img key={index} src={FullStar} alt="Star" width={size} height={size} />
      ))}
      {[...Array(total - count)].map((_, index) => (
        <img key={`empty_${index}`} src={EmptyStar} alt="Empty Star" width={size} height={size} />
      ))}
    </>
  );
}

export default Stars;
