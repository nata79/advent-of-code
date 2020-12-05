def find_space(moves, from, to)
  return from if moves.empty?
  return find_space(moves.slice(1, moves.size), from, to - ((to - from) / 2.0).ceil) if %w[F L].include?(moves[0])

  find_space(moves.slice(1, moves.size), from + ((to - from) / 2.0).ceil, to)
end

boarding_passes = File.read('day5.txt').split("\n")

seats = boarding_passes.map do |boarding_pass|
  row = find_space(boarding_pass.slice(0, 7).chars, 0, 127)
  column = find_space(boarding_pass.slice(7, boarding_pass.size).chars, 0, 7)
  (row * 8) + column
end

p seats.max

available_seats = (8...(127 * 8)).to_a.filter do |seat|
  !seats.include?(seat) && seats.include?(seat - 1) && seats.include?(seat + 1)
end

p available_seats
