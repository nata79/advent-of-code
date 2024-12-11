stones = File.read('day11.txt').split(' ').map(&:to_i)

def split_number(num, num_digits)
  div = 10 ** (num_digits / 2)
  left_half = num / div
  right_half = num % div
  [left_half, right_half]
end

def blink(stones, blinks)
  stones_hash = Hash.new(0)
  stones.each {  stones_hash[_1] += 1 }  
  blinks.times do |i|
    new_stones = stones_hash.dup
    
    stones_hash.each do |stone, count|
      if stone == 0
        new_stones[1] += count
      elsif (Math.log10(stone).floor + 1).even?
        left_half, right_half = split_number(stone, Math.log10(stone).floor + 1)
        new_stones[left_half] += count
        new_stones[right_half] += count
      else
        new_stones[stone * 2024] += count
      end
      new_stones[stone] -= count
      new_stones.delete(stone) if new_stones[stone] == 0
    end
    
    stones_hash = new_stones
  end

  stones_hash.values.sum
end

p blink(stones, 25)
p blink(stones, 75)
