reports = []

File.read('day2.txt').split("\n").map do |line|
  reports << line.split(' ').map(&:to_i)
end

def safe(report, max_errors = 0)
  direction = report[0] > report[1] ? 'down' : 'up'
  
  report.each_with_index do |level, i|
    if i > 0
      if direction == 'up'
        if level < report[i - 1]
          return false
        end
      elsif level > report[i - 1]
          return false
      end

      if report[i] == report[i - 1]
        return false
      end

      if (report[i] - report[i - 1]).abs > 3
        return false
      end
    end
  end

  true
end

p reports.filter { |report| safe(report) }.size

valid_reports = reports.filter do |report|
  commbinations = [report]
  (0..report.size - 1).each do |i|
    combination = report.dup
    combination.delete_at(i)
    commbinations << combination
  end

  commbinations.filter { |combination| safe(combination) }.size > 0
end

p valid_reports.size