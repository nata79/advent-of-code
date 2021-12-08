input = '16,1,2,0,4,2,7,1,2,14'

positions = File.read('day7.txt').split(',').map(&:to_i)
# positions = input.split(',').map(&:to_i)

min = positions.min
max = positions.max

best_position = nil
fuel = nil

(min..max).each do |position|
  current_fuel = positions.map { (_1 - position).abs }.sum
  if best_position.nil? || current_fuel < fuel
    fuel = current_fuel
    best_position = position
  end
end

p fuel

best_position = nil
fuel = nil

(min..max).each do |position|
  current_fuel = positions.map { (0..(_1 - position).abs).sum }.sum
  if best_position.nil? || current_fuel < fuel
    fuel = current_fuel
    best_position = position
  end
end

p fuel