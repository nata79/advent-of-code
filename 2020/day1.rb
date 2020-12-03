numbers = File.read('day1.txt').split("\n").map(&:to_i)

numbers.each_with_index do |x, i|
  numbers.slice(i + 1, numbers.size).each_with_index do |y, j|
    next unless x + y <= 2020

    numbers.slice(j + 1, numbers.size).each do |z|
      if x + y + z == 2020
        p x * y * z
        return
      end
    end
  end
end
