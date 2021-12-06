CACHE = {}

def direct_children(fish, total_days)
  return CACHE["#{fish}-#{total_days}"] if CACHE["#{fish}-#{total_days}"]
  
  return 0 if total_days < fish
  
  current_fish = fish
  new_fish = 0

  total_days.times do |i|
    if current_fish == 0
      current_fish = 6
      new_fish += 1 + direct_children(8, total_days - i - 1)
    else
      current_fish -= 1
    end
  end

  p "#{fish} - #{total_days} - #{new_fish}"
  CACHE["#{fish}-#{total_days}"] = new_fish
  new_fish
end

p File.read('day6.txt').split(',').map(&:to_i).map { |fish| 1 + direct_children(fish, 256) }.sum