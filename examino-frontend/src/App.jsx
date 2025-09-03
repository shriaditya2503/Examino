import { useState } from 'react'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-6">
      <div className="w-full max-w-md rounded-xl border border-gray-200 bg-white shadow-sm">
        <div className="p-6">
          <h1 className="text-2xl font-semibold text-gray-900">React + Tailwind</h1>
          <p className="mt-1 text-sm text-gray-600">Vite project wired with Tailwind CSS.</p>
          <div className="mt-6">
            <button
              className="inline-flex items-center gap-2 rounded-lg bg-indigo-600 px-4 py-2 text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
              onClick={() => setCount((prev) => prev + 1)}
            >
              Clicked {count} times
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default App
